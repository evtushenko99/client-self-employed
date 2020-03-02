package com.example.client_self_employed.presentation.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.impl.utils.SynchronousExecutor;

import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит тесты на {@link FindExpertViewModel}
 */
public class FindExpertViewModelTest {
    private FindExpertViewModel mViewModel;
    private ExpertInteractor mInteractor;
    private ResourceWrapper mResourceWrapper;
    private List<Expert> mExperts;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно

    @Before
    public void setUp() {
        mInteractor = mock(ExpertInteractor.class);
        mResourceWrapper = mock(ResourceWrapper.class);
        mViewModel = new FindExpertViewModel(mInteractor, new SynchronousExecutor(), mResourceWrapper);
        mExperts = new ArrayList<>(createExpert());
    }

    private List<Expert> createExpert() {
        List<Expert> experts = new ArrayList<>();
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5, null);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2, null);
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10, null);
        experts.add(expert1);
        experts.add(expert2);
        experts.add(expert3);
        return experts;
    }

    @Test
    public void loadAllExperts() {
        //arrange
        //act
        mViewModel.loadAllExperts();
        //assert
        Mockito.verify(mInteractor).loadAllExperts(mViewModel.getExpertCallBack());
    }

    @Test
    public void setSearchQuery_notNull() {
        //arrange
        String searchQuery = "Ма";
        List<Expert> expected = expectedExperts();
        when(mInteractor.findExpert(mExperts, searchQuery)).thenReturn(expected);
        //act
        mViewModel.getExpertCallBack().expertsIsLoaded(createExpert());
        mViewModel.setSearchQuery(searchQuery);
        //assert
        verify(mInteractor).findExpert(mExperts, searchQuery);
        assertThat(mViewModel.getLiveData().getValue(), is(expected));
    }

    @Test
    public void setSearchQuery_null() {
        //arrange
        String searchQuery = "";
        List<Expert> expected = new ArrayList<>();
        when(mInteractor.findExpert(mExperts, searchQuery)).thenReturn(expected);
        //act
        mViewModel.setSearchQuery(searchQuery);
        //assert
        assertThat(mViewModel.getLiveData().getValue(), is(expected));
    }

    private List<Expert> expectedExperts() {
        List<Expert> experts = new ArrayList<>();
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5, null);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2, null);
        experts.add(expert1);
        experts.add(expert2);
        return experts;
    }

    @Test
    public void expertCallback_happyCase() {
        //arrange
        //act
        mViewModel.getExpertCallBack().expertsIsLoaded(mExperts);
        //assert
        assertThat(mViewModel.getIsLoading().getValue(), is(false));
        assertThat(mViewModel.getLiveData().getValue(), is(mExperts));
    }

    @Test
    public void expertCallback_exceptionCase() {
        //arrange
        String message = "message";
        //act
        mViewModel.getExpertCallBack().messageLoadingExperts(message);
        //assert
        assertThat(mViewModel.getErrors().getValue(), is(message));
    }
}
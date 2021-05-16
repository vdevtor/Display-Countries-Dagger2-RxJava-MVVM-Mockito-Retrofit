package com.example.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countries.model.CountriesService
import com.example.countries.model.Country
import com.example.countries.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSucess() {
        val country = Country("countryName", "capital", "url")
        val countryList = arrayListOf(country)
        testSingle = Single.just(countryList)

        `when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()

        assertEquals(1, listViewModel.countries.value?.size)
        assertEquals(false, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountriesFail() {
        testSingle = Single.error(Throwable())

        `when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        assertEquals(true, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun schedulePeriodicallyDirect(run: Runnable?, initialDelay: Long, period: Long, unit: TimeUnit?): Disposable {
                return super.schedulePeriodicallyDirect(run, 0, period, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler> -> immediate }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

    }
}
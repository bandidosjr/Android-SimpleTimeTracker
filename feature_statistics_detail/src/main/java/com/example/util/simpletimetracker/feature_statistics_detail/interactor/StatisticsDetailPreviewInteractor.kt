package com.example.util.simpletimetracker.feature_statistics_detail.interactor

import com.example.util.simpletimetracker.domain.interactor.CategoryInteractor
import com.example.util.simpletimetracker.domain.interactor.PrefsInteractor
import com.example.util.simpletimetracker.domain.interactor.RecordTypeInteractor
import com.example.util.simpletimetracker.domain.model.ChartFilterType
import com.example.util.simpletimetracker.feature_statistics_detail.mapper.StatisticsDetailViewDataMapper
import com.example.util.simpletimetracker.feature_statistics_detail.viewData.StatisticsDetailPreviewViewData
import com.example.util.simpletimetracker.navigation.params.TypesFilterParams
import javax.inject.Inject

class StatisticsDetailPreviewInteractor @Inject constructor(
    private val prefsInteractor: PrefsInteractor,
    private val recordTypeInteractor: RecordTypeInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val statisticsDetailViewDataMapper: StatisticsDetailViewDataMapper
) {

    suspend fun getPreviewData(
        filterParams: TypesFilterParams
    ): StatisticsDetailPreviewViewData {
        val id = filterParams.selectedIds.firstOrNull()
        val filter = filterParams.filterType
        val isDarkTheme = prefsInteractor.getDarkMode()

        if (id == null) {
            return statisticsDetailViewDataMapper.mapToPreviewEmpty(isDarkTheme)
        }

        val name: String?
        val color: Int?
        val icon: String?

        when (filter) {
            ChartFilterType.ACTIVITY -> {
                val recordType = recordTypeInteractor.get(id)
                name = recordType?.name
                color = recordType?.color
                icon = recordType?.icon
            }
            ChartFilterType.CATEGORY -> {
                val category = categoryInteractor.get(id)
                name = category?.name
                color = category?.color
                icon = null
            }
        }

        return statisticsDetailViewDataMapper.mapToPreview(name, icon, color, isDarkTheme)
    }
}
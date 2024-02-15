package com.synrgyacademy.data.remote.response

import com.google.gson.annotations.SerializedName
import com.synrgyacademy.domain.model.airport.PromotionsDataModel

data class PromotionResponse(

    @field:SerializedName("data")
    val data: PromotionItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class PromotionData(

    @field:SerializedName("content")
    val content: List<PromotionItem>? = null,

    @field:SerializedName("pageable")
    val pageable: Pageable? = null,

    @field:SerializedName("last")
    val last: Boolean? = null,

    @field:SerializedName("totalElements")
    val totalElements: Int? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("number")
    val number: Int? = null,

    @field:SerializedName("sort")
    val sort: Sort? = null,

    @field:SerializedName("first")
    val first: Boolean? = null,

    @field:SerializedName("numberOfElements")
    val numberOfElements: Int? = null,

    @field:SerializedName("empty")
    val empty: Boolean? = null

)

data class PromotionItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("discount")
    val discount: Int? = null,

    @field:SerializedName("terms")
    val terms: String? = null,

    @field:SerializedName("startDate")
    val startDate: String? = null,

    @field:SerializedName("endDate")
    val endDate: String? = null
) {
    fun toPromotionsDataModel(): PromotionsDataModel =
        PromotionsDataModel(
            id = id.orEmpty(),
            title = title.orEmpty(),
            description = description.orEmpty(),
            code = code.orEmpty(),
            discount = discount ?: 0,
            terms = terms.orEmpty(),
            startDate = startDate.orEmpty(),
            endDate = endDate.orEmpty()
        )

}
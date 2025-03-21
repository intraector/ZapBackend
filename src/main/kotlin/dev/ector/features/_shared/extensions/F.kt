package dev.ector.features._shared.extensions

/// F stands for "Field"
object F {
    const val PAGE_NUMBER = "page_number"
    const val PAGE_SIZE = "page_size"
    const val SEARCH_QUERY = "query"
    const val BRAND_ID = "brand_id"
    const val MODEL_ID = "model_id"
    const val BODY_ID = "body_id"
    const val GENERATION_ID = "generation_id"
    const val MODIFICATION_ID = "modification_id"
    const val REGION = "region"
    const val REGION_ID = "region_id"
    const val REGION_NAME = "region_name"
    const val ID = "id"
    const val DESCRIPTION = "description"
    const val NO_MORE_PAGES = "no_more_pages"
    const val DATA = "data"
    const val NAME = "name"
    const val YEAR_FROM = "year_from"
    const val YEAR_TO = "year_to"
    const val ZAP_ID = "zap_id"
    const val PHOTOS = "photos"
    const val SPARES = "spares"
    const val ZAP = "zap"
    const val SPARE_ID = "spare_id"
    const val ENABLED = "enabled"
    const val PHONE = "phone"
    const val CREATED_AT = "created"
    const val CODE = "code"


}

val String.param: String
    get() = "{$this}"
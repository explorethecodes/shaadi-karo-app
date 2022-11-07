package com.fincare.shaadikaro.data.network.models.collection.suggestions

import com.fincare.shaadikaro.data.network.utils.CallCode
import com.fincare.shaadikaro.data.network.models.base.BaseRequest

data class SuggestionsRequest(
    var callCode: CallCode = CallCode.SUGGESTIONS
) : BaseRequest()
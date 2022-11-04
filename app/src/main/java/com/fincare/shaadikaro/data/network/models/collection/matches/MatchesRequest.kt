package com.fincare.shaadikaro.data.network.models.collection.matches

import com.fincare.shaadikaro.data.network.utils.CallCode
import com.fincare.shaadikaro.data.network.models.base.BaseRequest

data class MatchesRequest(
    var callCode: CallCode = CallCode.MATCHES
) : BaseRequest()
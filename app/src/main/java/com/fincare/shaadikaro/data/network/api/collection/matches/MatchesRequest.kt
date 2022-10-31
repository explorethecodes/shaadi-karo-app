package com.fincare.shaadikaro.data.network.api.collection.matches

import com.fincare.shaadikaro.data.network.CallCode
import com.fincare.shaadikaro.data.network.api.base.BaseRequest

data class MatchesRequest(
    var callCode: CallCode = CallCode.MATCHES
) : BaseRequest()
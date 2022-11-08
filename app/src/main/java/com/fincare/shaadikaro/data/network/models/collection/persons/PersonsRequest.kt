package com.fincare.shaadikaro.data.network.models.collection.persons

import com.fincare.shaadikaro.data.network.utils.CallCode
import com.fincare.shaadikaro.data.network.models.base.BaseRequest

data class PersonsRequest(
    var callCode: CallCode = CallCode.SUGGESTIONS
) : BaseRequest()
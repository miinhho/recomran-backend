package io.miinhho.recomran.api.exception

import io.miinhho.recomran.common.exception.APIStatusException
import io.miinhho.recomran.common.response.APIStatusCode

class KakaoAPIException() : APIStatusException(
    status = APIStatusCode.API_SERVICE_DOWN
)
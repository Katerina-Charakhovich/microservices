package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "return file by resource id"

    request {
        url "/resources/1"
        method GET()
    }

    response {
        status OK()
    }

}
package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "add new file"

    request {
        url "/songs"
        method POST()
        headers {
            contentType applicationJson()
        }
        body("name": "test_name",
                "artist": "test_artist",
                "album": "test_album",
                "length": 10,
                "resourceId": 1L,
                "year": 10)
    }
    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body("id": 1L,
                "name": "test_name",
                "artist": "test_artist",
                "album": "test_album",
                "length": 10,
                "resourceId": 1L,
                "year": 10)
    }

}
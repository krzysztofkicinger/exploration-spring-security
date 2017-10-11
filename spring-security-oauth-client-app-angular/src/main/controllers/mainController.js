angular.module("clientAppAngular")
    .controller("mainController", function($scope, $http, $httpParamSerializer) {

        $scope.data = {
            grant_type: 'password',
            username: "",
            password: "",
            client_id: "passwordClient"     // From the spring-security-oauth-authorization-server configuration
        };

        $scope.encoded = btoa($scope.data + ":secret");

        /**
         * Method for PASSWORD FLOW
         *
         *  - To get AccessToken we send a POST to the /oauth/token
         *  - Basic client credentials are set to hit this endpoint (encoded to Base64)
         *  - Access token is send in the cookie after correct registration
         */
        $scope.login = function() {

            const tokenRequest = {
                method: "POST",
                url: "http://localhost:9000/oauth/token",
                headers: {
                    "Authorization": "Basic "  + $scope.encoded,
                    "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
                },
                data: $httpParamSerializer($scope.data)
            };

            $http(tokenRequest).then(function(data) {
                const accessToken = data.data.access_token;
                $http.defaults.headers.common.Authorization = "Bearer " + accessToken;
                $cookies.put("access_token", accessToken);
                window.location.href = "index";

            });

        }


    });

const app = angular.module("clientApp", ["ngRoute", "ngResource", "ngCookies"]);

app.controller("mainController", function($scope, $http, $httpParamSerializer, $cookies) {

    $scope.data = {
        grant_type: "password",
        username: "",
        password: "",
        client_id: "passwordClient",     // From the spring-security-oauth-authorization-server configuration
        foo: {
            id: "",
            name: ""
        }
    };

    $scope.encoded = btoa($scope.data.client_id + ":secret");

    $scope.login = function() {

        const tokenRequest = {
            method: "POST",
            url: "http://localhost:9000/oauth/token",
            headers: {
                "Authorization": "Basic "  + $scope.encoded,
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: $httpParamSerializer({
                grant_type: "password",
                username: $scope.data.username,
                password: $scope.data.password,
                client_id: $scope.data.client_id
            })
        };

        $http(tokenRequest)
            .then(function(data) {
                const accessToken = data.data.access_token;
                $http.defaults.headers.common.Authorization = "Bearer " + accessToken;
                $cookies.put("access_token", accessToken);
                $scope.getFoo();
                window.location.href = "/";
            }).catch(function(error, response) {
            console.log(error);
            console.log(response);
        });

    };

    $scope.getFoo = function() {
        $http.get("http://localhost:9001/foos/123")
            .then(function(data) {
                console.log(data);
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    var isLoginPage = window.location.href.indexOf("login") != -1;

    if(isLoginPage) {
        console.log("Login Page");
        if($cookies.get("access_token")) {
            window.location.href = "index";
        }
    } else {
        if($cookies.get("access_token")) {
            $http.defaults.headers.common.Authorization = "Bearer " + $cookies.get("access_token");
            $scope.getFoo();
        } else {
            console.log("Go to login page");
            window.location.href = "login";
        }
    }

});
var testApp = angular.module('testApp', []);

testApp.controller('testController' , function ($scope, $http) {

//    Our GET request function
    $scope.getRequest = function () {
        console.log("I've been pressed!");
        $http.get("http://localhost:7071/api/users/")
            .then(function successCallback(response){
                console.log("Successfully POST-ed data");
                $scope.response = response;
            }, function errorCallback(response){
                console.log("Unable to perform get request");
            });
    };

//    Our POST request function
    $scope.postRequest = function () {
        bodyReq={
            "firstName":$scope.firstName,
            "lastName":$scope.lastName,
            "email":$scope.email,
            "password":$scope.password
        };
        $http.post("http://localhost:7071/api/users/", bodyReq)
            .then(function successCallback(response){
                console.log("Successfully POST-ed data");
                $scope.firstName='';
                $scope.lastName='';
                $scope.email='';
                $scope.password='';
            }, function errorCallback(response){
                console.log("POST-ing of data failed");
            });
    };

});
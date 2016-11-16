angular.module("app", []);

angular.module("app").controller("SearchController", ['$http', '$scope', function ($http, $scope) {

        $scope.getResults = function (text, min, max) {
            var priceRange = "&p=" + min + "-" + max + "";

            if (angular.isUndefined(min) || angular.isUndefined(max)) {
                priceRange = "";
            }

            $http.get("http://localhost:8084/UsedRunner/api/ad/q=" + text + priceRange)
                    .then(function (success) {
                        $scope.results = success.data;
                    }, function (error) {
                        $scope.error = error.data;
                    });
        };

    }]);
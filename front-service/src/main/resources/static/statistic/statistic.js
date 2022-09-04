angular.module('market-front').controller('statisticController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/gateway/statistic';

    $scope.getStatistic = function () {
        $http.post(contextPath)
            .then(function (response) {
                $scope.statistic = response.data;
                console.log(response.data);
            });
    }

    $scope.getStatistic();
});
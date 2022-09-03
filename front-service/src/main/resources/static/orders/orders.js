angular.module('market-front').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/gateway/api/v1';

    $scope.loadOrders = function () {
        $http.get(contextPath + '/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }
    $scope.loadOrders();
});
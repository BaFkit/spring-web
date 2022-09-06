angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {

    $scope.loadCart = function () {
        $http.post('http://localhost:5555/cart/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.clearCart = function () {
        $http.post('http://localhost:5555/cart/api/v1/carts/clear', $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decreaseFromCart = function (productId) {
        $http.post('http://localhost:5555/cart/api/v1/carts/decrease/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.deleteFromCart = function (productId) {
        $http.post('http://localhost:5555/cart/api/v1/carts/delete/' + productId, $localStorage.cartName)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $http({
            url: 'http://localhost:5555/gateway/api/v1/orders/' + $localStorage.cartName,
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null
            $scope.clearCart();
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();

});
angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, $routeParams) {

    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/orders/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
        });
    };

    $scope.renderPaymentButtons = function () {
        $http({
            url: 'http://localhost:5555/orders/api/v1/qiwi/create/' + $scope.order.id,
            method: 'GET'
        }).then(function (response) {
            $scope.billid = response.data.billid;
            params = {
                payUrl: response.data.responseUrl,
            }
            QiwiCheckout.openInvoice(params)
                .then(function (onFullField){
                    $scope.sendBillId();
                    $location.path("http://localhost:3000/front")
                })
                .then(function (onRejection){
                    $location.path("http://localhost:3000/front")
                });

        });
    };
    $scope.sendBillId = function () {
        $http.post('http://localhost:5555/orders/api/v1/qiwi/capture/' + $scope.billid);
    };


    $scope.loadOrder();
});
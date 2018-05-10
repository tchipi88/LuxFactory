(function() {
    'use strict';
    angular
        .module('app')
        .factory('MouvementStockInit', MouvementStockInit);

    MouvementStockInit.$inject = ['$resource','DateUtils'];

    function MouvementStockInit ($resource,DateUtils) {
        var resourceUrl =  'api/mouvement-stock-inits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.firstDate =DateUtils.convertLocalDateFromServer(data.firstDate);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.firstDate =DateUtils.convertLocalDateToServer(copy.firstDate);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.firstDate =DateUtils.convertLocalDateToServer(copy.firstDate);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();

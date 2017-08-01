(function() {
    'use strict';
    angular
        .module('app')
        .factory('MouvementStockOut', MouvementStockOut);

    MouvementStockOut.$inject = ['$resource','DateUtils'];

    function MouvementStockOut ($resource,DateUtils) {
        var resourceUrl =  'api/mouvement-stock-outs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
             'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                          data.dateTransaction =DateUtils.convertLocalDateFromServer(data.dateTransaction);

                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateTransaction =DateUtils.convertLocalDateToServer(copy.dateTransaction);

                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                     copy.dateTransaction =DateUtils.convertLocalDateToServer(copy.dateTransaction);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();

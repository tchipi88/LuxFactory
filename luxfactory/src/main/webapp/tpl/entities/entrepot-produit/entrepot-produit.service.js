(function() {
    'use strict';
    angular
        .module('app')
        .factory('EntrepotProduit', EntrepotProduit);

    EntrepotProduit.$inject = ['$resource','DateUtils'];

    function EntrepotProduit ($resource,DateUtils) {
        var resourceUrl =  'api/entrepot-produits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

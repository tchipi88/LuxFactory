(function() {
    'use strict';
    angular
        .module('app')
        .factory('Fournisseur', Fournisseur);

    Fournisseur.$inject = ['$resource','DateUtils'];

    function Fournisseur ($resource,DateUtils) {
        var resourceUrl =  'api/fournisseurs/:id';

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

(function() {
    'use strict';
    angular
        .module('app')
        .factory('Compte', Compte);

    Compte.$inject = ['$resource','DateUtils'];

    function Compte ($resource,DateUtils) {
        var resourceUrl =  'api/comptes/:id';

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

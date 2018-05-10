(function() {
    'use strict';
    angular
        .module('app')
        .factory('Procesverbal', Procesverbal);

    Procesverbal.$inject = ['$resource','DateUtils'];

    function Procesverbal ($resource,DateUtils) {
        var resourceUrl =  'api/procesverbals/:id';

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

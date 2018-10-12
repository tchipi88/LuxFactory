(function() {
    'use strict';

    angular
        .module('app')
        .factory('EmployeList', EmployeList);

    EmployeList.$inject = ['$resource'];

    function EmployeList($resource) {
        var resourceUrl =  'api/list/employes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

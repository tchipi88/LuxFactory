(function() {
    'use strict';

    angular
        .module('app')
        .factory('EmployeSearch', EmployeSearch);

    EmployeSearch.$inject = ['$resource'];

    function EmployeSearch($resource) {
        var resourceUrl =  'api/_search/employes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

(function() {
    'use strict';

    angular
        .module('app')
        .factory('ActiviteList', ActiviteList);

    ActiviteList.$inject = ['$resource'];

    function ActiviteList($resource) {
        var resourceUrl =  'api/list/activites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

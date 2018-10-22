(function() {
    'use strict';

    angular
        .module('app')
        .factory('UniteList', UniteList);

    UniteList.$inject = ['$resource'];

    function UniteList($resource) {
        var resourceUrl =  'api/list/unites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

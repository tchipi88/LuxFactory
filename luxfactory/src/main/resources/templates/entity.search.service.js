(function() {
    'use strict';

    angular
        .module('app')
        .factory('[(${entity})]Search', [(${entity})]Search);

    [(${entity})]Search.$inject = ['$resource'];

    function [(${entity})]Search($resource) {
        var resourceUrl =  'api/_search/[(${entity_url})]s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

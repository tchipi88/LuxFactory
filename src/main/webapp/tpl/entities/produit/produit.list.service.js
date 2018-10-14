(function() {
    'use strict';

    angular
        .module('app')
        .factory('ProduitList', ProduitList);

    ProduitList.$inject = ['$resource'];

    function ProduitList($resource) {
        var resourceUrl =  'api/list/produits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();

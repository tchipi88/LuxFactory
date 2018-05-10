(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('admin', {
            abstract: true,
            parent: 'app',
            // Note: abstract still needs a ui-view for its children to populate.
            // You can simply add it inline here.
            template: '<ui-view/>'
        });
    }
})();

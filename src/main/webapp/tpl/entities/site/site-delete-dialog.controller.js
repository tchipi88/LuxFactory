(function() {
    'use strict';

    angular
        .module('app')
        .controller('SiteDeleteController',SiteDeleteController);

    SiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Site'];

    function SiteDeleteController($uibModalInstance, entity, Site) {
        var vm = this;

        vm.site = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Site.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

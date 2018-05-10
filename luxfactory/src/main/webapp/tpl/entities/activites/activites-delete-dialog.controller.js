(function() {
    'use strict';

    angular
        .module('app')
        .controller('ActivitesDeleteController',ActivitesDeleteController);

    ActivitesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Activites'];

    function ActivitesDeleteController($uibModalInstance, entity, Activites) {
        var vm = this;

        vm.activites = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Activites.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

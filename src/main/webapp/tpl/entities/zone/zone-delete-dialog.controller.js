(function() {
    'use strict';

    angular
        .module('app')
        .controller('ZoneDeleteController',ZoneDeleteController);

    ZoneDeleteController.$inject = ['$uibModalInstance', 'entity', 'Zone'];

    function ZoneDeleteController($uibModalInstance, entity, Zone) {
        var vm = this;

        vm.zone = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Zone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

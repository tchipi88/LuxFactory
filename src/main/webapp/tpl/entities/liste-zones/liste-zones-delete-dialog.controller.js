(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeZonesDeleteController',ListeZonesDeleteController);

    ListeZonesDeleteController.$inject = ['$uibModalInstance', 'entity', 'ListeZones'];

    function ListeZonesDeleteController($uibModalInstance, entity, ListeZones) {
        var vm = this;

        vm.listeZones = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ListeZones.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

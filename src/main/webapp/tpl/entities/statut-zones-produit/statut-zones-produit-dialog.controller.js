(function() {
    'use strict';

    angular
        .module('app')
        .controller('StatutZonesProduitDialogController', StatutZonesProduitDialogController);

    StatutZonesProduitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'StatutZonesProduit'];

    function StatutZonesProduitDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, StatutZonesProduit ) {
        var vm = this;

        vm.statutZonesProduit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.statutZonesProduit.id !== null) {
                StatutZonesProduit.update(vm.statutZonesProduit, onSaveSuccess, onSaveError);
            } else {
                StatutZonesProduit.save(vm.statutZonesProduit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:statutZonesProduitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.statutZonesProduit[fieldName] = base64Data;
                            vm.statutZonesProduit[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.statutZonesProduit[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();

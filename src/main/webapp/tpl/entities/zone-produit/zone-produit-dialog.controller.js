(function() {
    'use strict';

    angular
        .module('app')
        .controller('ZoneProduitDialogController', ZoneProduitDialogController);

    ZoneProduitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'ZoneProduit','Zone','Produit'];

    function ZoneProduitDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, ZoneProduit ,Zone,Produit) {
        var vm = this;

        vm.zoneProduit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.produits = Produit.query();
vm.zones = Zone.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.zoneProduit.id !== null) {
                ZoneProduit.update(vm.zoneProduit, onSaveSuccess, onSaveError);
            } else {
                ZoneProduit.save(vm.zoneProduit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:zoneProduitUpdate', result);
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
                            vm.zoneProduit[fieldName] = base64Data;
                            vm.zoneProduit[fieldName + 'ContentType'] = $file.type;
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
                        vm.zoneProduit[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();

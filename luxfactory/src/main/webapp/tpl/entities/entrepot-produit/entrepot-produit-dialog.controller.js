(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotProduitDialogController', EntrepotProduitDialogController);

    EntrepotProduitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'EntrepotProduit','Entrepot','Produit'];

    function EntrepotProduitDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, EntrepotProduit ,Entrepot,Produit) {
        var vm = this;

        vm.entrepotProduit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.produits = Produit.query();
        vm.entrepots = Entrepot.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrepotProduit.id !== null) {
                EntrepotProduit.update(vm.entrepotProduit, onSaveSuccess, onSaveError);
            } else {
                EntrepotProduit.save(vm.entrepotProduit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:entrepotProduitUpdate', result);
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
                            vm.entrepotProduit[fieldName] = base64Data;
                            vm.entrepotProduit[fieldName + 'ContentType'] = $file.type;
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
                        vm.entrepotProduit[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();

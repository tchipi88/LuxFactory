(function() {
    'use strict';

    angular
        .module('app')
        .controller('[(${entity})]DialogController', [(${entity})]DialogController);

    [(${entity})]DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', '[(${entity})]'[(${entity_selects_header})]];

    function [(${entity})]DialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, [(${entity})] [(${entity_selects_header1})]) {
        var vm = this;

        vm.[(${entity_var})] = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        [(${entity_selects})]
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.[(${entity_var})].id !== null) {
                [(${entity})].update(vm.[(${entity_var})], onSaveSuccess, onSaveError);
            } else {
                [(${entity})].save(vm.[(${entity_var})], onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:[(${entity_var})]Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        [(${entity_dates})]
        
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
                            vm.[(${entity_var})][fieldName] = base64Data;
                            vm.[(${entity_var})][fieldName + 'ContentType'] = $file.type;
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
                        vm.[(${entity_var})][fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();

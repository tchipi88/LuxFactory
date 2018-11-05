(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeZonesController', ListeZonesController);

    ListeZonesController.$inject = ['Employe','$state', 'DataUtils', 'ListeZones',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','EntrepotList','ActiviteList','EmployeList'];

    function ListeZonesController(Employe,$state, DataUtils, ListeZones,  ParseLinks, AlertService, paginationConstants, pagingParams,EntrepotList,ActiviteList,EmployeList) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.entrepots = EntrepotList.query();
        vm.activitess = ActiviteList.query();
        vm.employes = EmployeList.query();

        function loadEmployes(){

            var employesCount;

            Employe.get({},onSuccess,onError);
            function onSuccess(data, headers){
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                employesCount = vm.totalItems;
                vm.employes = data;
                
                //vm.employes = EmployeList.query({size: employesCount});
            }
            function onError(error){
                AlertService.error(error.data.message);
            }

            //alert("emplo: " + vm.employesCount);
        };

        //loadEmployes();
        
      
        vm.search = search;

        loadAll();

        function loadAll () {
                ListeZones.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listeZoness = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search()
        {
            // var dateFormat = 'yyyy-MM-dd';
            // var fromDate = $filter('date')(vm.fromDate, dateFormat) == null?"": $filter('date')(vm.fromDate, dateFormat);
            // var toDate = $filter('date')(vm.toDate, dateFormat)== null?"":$filter('date')(vm.toDate, dateFormat);
             var selected_activite = vm.activite == null ? "" : vm.activite.libelle;
             var selected_entrepot = vm.zone == null ? "" : vm.zone.libelle;
             var selected_responsable = vm.responsable == null ? "":vm.responsable.nom;

            ListeZones.query({
            page: vm.page - 1,
            size: vm.itemsPerPage,
            activite: selected_activite,
            entrepot: selected_entrepot,
            responsable: selected_responsable
        },  function (data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.listeZoness = data;
        });
            
            
           
        }


        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
            });
        }

       

        function clear() {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.transition();
        }
    }
})();

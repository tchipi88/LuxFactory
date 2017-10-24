(function() {
    'use strict';

    angular
        .module('app')
        .controller('ListeArticlesController', ListeArticlesController);

    ListeArticlesController.$inject = ['$state', 'DataUtils', 'ListeArticles','Activites','Employe',  'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','$filter', 'Entrepot', 'Produit'];

    function ListeArticlesController($state, DataUtils, ListeArticles,Activites, Employe, ParseLinks, AlertService, paginationConstants, pagingParams,$filter, Entrepot, Produit) {

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
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.entrepots = Entrepot.query();
        vm.activitess = Activites.query();
        vm.employes = Employe.query();
        vm.produits = Produit.query();
        vm.fromDate = new Date();
        vm.toDate = new Date();


        vm.search = search;

        loadAll();

        function loadAll () {
                ListeArticles.query({
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
                vm.listeArticless = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search()
        {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);

            ListeArticles.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                activite: vm.produit,
                entrepot: vm.entrepot,
                fromDate: fromDate, 
                toDate: toDate
            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listeArticless = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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

        
        vm.datePickerOpenStatus.dateFin = false;
        vm.datePickerOpenStatus.dateDebut = false;

        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

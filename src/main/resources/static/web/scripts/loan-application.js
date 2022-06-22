Vue.createApp({
    data() {
        return {
        client:{},
        stringUpper: "",
        loans:[],
        tipoPrestamo: "",
        cuotasElegida: "",
        prestamoElegido: {},
        cuotas: [],
        montoMaximo : 0,
        cuotaElegida: "",
        montoSolicitado: 0,
        cuotaAPagar: 0,
        cuentaDestino: "",
        cuentas: []
        }
    },

    created(){
            axios.get(`http://localhost:8080/api/loans`)
            .then(result =>{
                this.loans = result.data
                }),

                axios.get(`http://localhost:8080/api/clients/current`)
                .then(result =>{
                    this.client = result.data
                    this.cuentas = result.data.accounts
                    }
                )
    },

    methods:{

        solicitarPrestamo () {
            if(this.tipoPrestamo != "" && this.cuotaElegida != "" && this.montoSolicitado != "" && this.cuentaDestino != ""){
                axios.post('/api/loans',{ idLoan: this.tipoPrestamo, amount: this.montoSolicitado, payments: this.cuotaElegida , numberAccountDestiny:this.cuentaDestino})
                .then(response => {
                    Swal.fire('Prestamo solicitado','','success')
                    .then(res=> window.location.href = "/web/accounts.html", 5000)
                })
                .catch (error => {
                    Swal.fire({
                        icon:'error',
                        text: error.response.data,
                        })
                })
            }else {
                Swal.fire({
                    icon: 'error',
                    text: 'Seleccione una opcion en todos los campos',
                    footer: '<a href="./loan-application.html">Volver a intentar</a>'
                })
            }
        },
        primerLetraToUpper (string) {
            this.stringUpper = string.charAt(0).toUpperCase() + string.slice(1)
            return this.stringUpper
        },

        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        }
        
    },
    computed : {
        
        mostrarPrestamoElegido () {
            this.prestamoElegido = this.loans.filter(loan => loan.id == this.tipoPrestamo)
            this.prestamoElegido = this.prestamoElegido[0]
            if (this.prestamoElegido != null){
                this.cuotas = this.prestamoElegido.payments
                this.montoMaximo = this.prestamoElegido.maxAmount
            }
        },
        montoPorCuota (){
            parseInt(this.cuotaElegida)
            if ( this.montoSolicitado != 0 && this.cuotaElegida != 0){
            this.cuotaAPagar = (this.montoSolicitado *(this.prestamoElegido.percentage + 1))/ this.cuotaElegida
            this.cuotaAPagar = this.cuotaAPagar.toFixed(2)
            } else {
                this.cuotaAPagar = 0
            }
        }

    }

    }).mount('#app')
Vue.createApp({
    data() {
        return {
            dataClient: [],
            client:{},
            stringUpper: "",
            nombrePrestamo: "",
            montoMaxPosible: 0,
            arrayCuotas: "",
            porcentajeInteres: 0,
        }
    },


    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        .then(result =>{
            
            this.dataClient = result.data
            this.client = result.data
            
            }
        )
    },

    methods:{

        crearPrestamo () {
            if(this.nombrePrestamo != "" && this.montoMaxPosible != 0 && this.arrayCuotas  != "" && this.porcentajeInteres != 0){
                axios.post('/api/loans/admin',`name=${this.nombrePrestamo}&maxAmount=${this.montoMaxPosible}&payments=${this.arrayCuotas}&percentage=${this.porcentajeInteres}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                    Swal.fire('Tipo de prestamo creado','','success')
                    .then(res=> window.location.href = "/web/admin-loan.html", 5000)
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
                    footer: '<a href="./admin-loan.html">Volver a intentar</a>'
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

        cuotasEnArray (){
            if(this.arrayCuotas.includes(",")){
                this.arrayCuotas = this.arrayCuotas.split(",").map(Number)
            }else {
                this.arrayCuotas = this.arrayCuotas.split().map(Number)
            }
            this.arrayCuotas.filter(i => i > 0)
        },

    }

    }).mount('#app')
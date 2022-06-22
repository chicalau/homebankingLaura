Vue.createApp({
    data() {
        return {
        dataClient: [],
        cuentas:[],
        client:{},
        firstName:"",
        lastName:"",
        email:"",
        idCuenta: "",
        stringUpper: "",
        loans: [],
        transacciones:[],
        transaccionesArray: [],
        tipoDeCuenta: ""
        }
    },


    created(){
        axios.get(`/api/clients/current`)
        .then(result =>{
            this.dataClient = result.data
            this.cuentas = result.data.accounts
            this.client = result.data
            this.loans = result.data.loans
            }
        )
    },

    methods:{
        registerAccount (){
            if(this.tipoDeCuenta != "" ){
            axios.post('/api/clients/current/accounts',`accountType=${this.tipoDeCuenta}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                
                Swal.fire( {
                    icon: 'success',
                    text: 'Cuenta Creada'
                })
                .then(res=> window.location.href = "/web/accounts.html", 5000)
        })
        .catch (error => {
            Swal.fire({
                icon:'error',
                text: error.response.data,
                footer: '<a href="./accounts.html">Volver a intentar</a>'
            })
        })
            }else {
        Swal.fire({
            icon: 'error',
            text: 'Seleccione una opcion en todos los campos',
            footer: '<a href="./accounts.html">Volver a intentar</a>'
        })
    }
        },

        eliminarCuenta(cuentaSeleccionada){
            Swal.fire({
                title: `Estas seguro de eliminar la tarjeta ${cuentaSeleccionada.number} ${cuentaSeleccionada.accountType} ?`,
                icon: 'warning',
                showDenyButton: true,
                confirmButtonText: 'Eliminar cuenta',
                denyButtonText: `Cancelar`,
            }).then((result) => {
                
                if (result.isConfirmed) {
                    axios.patch('/api/accounts',`id=${cuentaSeleccionada.id}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(res=> {
                        Swal.fire( `Cuenta ${cuentaSeleccionada.number} ${cuentaSeleccionada.accountType} Eliminada`, '', 'success')
                        .then(res=> window.location.href = "/web/accounts.html", 5000)
                    }) 
                    .catch (error => {
                        Swal.fire(error.response.data, '', 'error')
                    })
                } else if (result.isDenied) {
                    Swal.fire('Cuenta no eliminada', '', 'error')
                    .then(res=> window.location.href = "/web/accounts.html", 5000)
                }
            })
        },

        primerLetraToUpper (string) {
            this.stringUpper = string.charAt(0).toUpperCase() + string.slice(1)
            return this.stringUpper
        },
        
        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        }
    },

    computed: {




        // ultimasTransacciones () {

        //     this.transacciones.push(...this.cuentas.flatMap(cuenta => cuenta.transactions))
        //     //this.transaccionesArray =[...new Array(this.transacciones)]
        //     console.log(this.transacciones)
        //     //     this.infocuentaOrigen = this.cuentas.filter(cuenta => cuenta.number == this.cuentaOrigen)
        //     //     this.transacciones = this.infocuentaOrigen.transactions
        //     //     console.log(this.transacciones)
        //     // },
        // }

    }



    }).mount('#app')
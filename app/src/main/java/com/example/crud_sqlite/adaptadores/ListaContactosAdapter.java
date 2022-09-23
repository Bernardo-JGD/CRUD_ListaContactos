package com.example.crud_sqlite.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_sqlite.R;
import com.example.crud_sqlite.VerActivity;
import com.example.crud_sqlite.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    //Declaro una lista como atributo del tipo de la clase Contactos
    ArrayList<Contactos> listaContactos;
    //Declaro otra lista auxiliar para la búsqueda
    ArrayList<Contactos> listaAux;

    //Este constructor llamo en el main y recibe la lista
    public ListaContactosAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos = listaContactos;
        listaAux = new ArrayList<>();
        //Respaldo la lista original
        listaAux.addAll(listaContactos);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);

        return new ContactoViewHolder(view);
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaContactos.clear();
            listaContactos.addAll(listaAux);
        }else{
            List<Contactos> coleccion = listaContactos.stream().filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
            listaContactos.clear();
            listaContactos.addAll(coleccion);

            /*Por si no funciona el anterior

            for(Contactos c: listaAux){
                if(c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                    listaContactos.add(c);
                }
            }
            * */
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ListaContactosAdapter.ContactoViewHolder holder, int position) {

        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono());
        holder.viewCorreo.setText(listaContactos.get(position).getCorreo_electronico());

    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder{

        TextView viewNombre, viewTelefono, viewCorreo;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);

            //aquí se envia el id del elemento seleccionado al activity donde se editará
            itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   Context context = view.getContext();
                   Intent intent = new Intent(context, VerActivity.class);
                   intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                   context.startActivity(intent);
               }
            });

        }
    }
}

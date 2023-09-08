package red.lisgar.proyecto.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import red.lisgar.proyecto.R;
import red.lisgar.proyecto.admin.AdminActualizarRutaActivity;
import red.lisgar.proyecto.usuario.UsuVisualizarRutaActivity;
import red.lisgar.proyecto.entidades.Ruta;

public class ListaRutasAdapter extends RecyclerView.Adapter<ListaRutasAdapter.RutasViewHolder> {

    ArrayList<Ruta> listaOriginal;
    ArrayList<Ruta> listItem;
    Context context;
    String destino;
    String ventana;

    public ListaRutasAdapter(ArrayList<Ruta> listItem, Context context, String destino, String ventana) {
        this.listItem = listItem;
        this.context = context;
        this.destino = destino;
        this.ventana = ventana;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listItem);
    }

    @NonNull
    @Override
    public RutasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        //CUAL LAYOUT SE UTILIZARA PARA EL ADAPTER
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutas, parent, false);
                return new RutasViewHolder(view);
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutas_user, parent, false);
                return new RutasViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + ventana);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RutasViewHolder holder, int position) {
        final Ruta item = listItem.get(position);

        //MANIPULACION DE DATOS EN EL LAYOUT
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                if(item.getTipo().equalsIgnoreCase("METROLINEA")){
                    holder.card.setVisibility(View.VISIBLE);
                }
                Glide.with(context)
                        .load(item.getFoto())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.imgRuta);
                holder.nombreRuta.setText(item.getNombre());
                holder.precioRuta.setText("$"+item.getPrecio());
                holder.descripcionRuta.setText("Descripción: "+item.getDescripcion());
                break;
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                if(item.getTipo().equalsIgnoreCase("METROLINEA")){
                holder.cardU.setVisibility(View.VISIBLE);
                }
                Glide.with(context)
                        .load(item.getFoto())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.imgRutaU);
                holder.nombre.setText(item.getNombre());
                holder.precio.setText("$"+item.getPrecio());
                holder.descripcion.setText(item.getDescripcion());
                break;
        }
    }

    //FILTRAR LA LISTA DE RUTAS DE ACUERDO AL NOMBRE DE LA RUTA
    public void filter(String buscar){
        int longitud = buscar.length();

        if(longitud == 0){
            listItem.clear();
            listItem.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Ruta> collection = listItem.stream().filter(i -> i.getNombre().toLowerCase().contains(buscar.toLowerCase())).collect(Collectors.toList());
                listItem.clear();
                listItem.addAll(collection);
            }else{
                for (Ruta c : listaOriginal){
                    if(c.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                        listaOriginal.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class RutasViewHolder extends RecyclerView.ViewHolder {

        ImageView card;
        ImageView imgRuta;
        TextView nombreRuta;
        TextView precioRuta;
        TextView descripcionRuta;

        ImageView cardU;
        ImageView imgRutaU;
        TextView nombre;
        TextView precio;
        TextView descripcion;


        public RutasViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            imgRuta = itemView.findViewById(R.id.imagenRutasDisponibles);
            nombreRuta = itemView.findViewById(R.id.txtItemNombreRuta);
            precioRuta = itemView.findViewById(R.id.txtItemPrecioRuta);
            descripcionRuta = itemView.findViewById(R.id.txtItemDescripcion);

            cardU = itemView.findViewById(R.id.card);
            imgRutaU = itemView.findViewById(R.id.imagenRutasPrestados);
            nombre = itemView.findViewById(R.id.txtItemNombre);
            descripcion = itemView.findViewById(R.id.txtItemDescripcion);
            precio = itemView.findViewById(R.id.txtItemPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    String id;

                    //DIRECCIONAMIENTO
                    switch (destino){
                        case "ACTUALIZAR":
                            intent = new Intent(context, AdminActualizarRutaActivity.class);
                            id = listItem.get(getAdapterPosition()).getId();
                            intent.putExtra("idi", id);
                            context.startActivity(intent);
                            break;
                        case "VERLIBRO":
                            intent = new Intent(context, UsuVisualizarRutaActivity.class);
                            id = listItem.get(getAdapterPosition()).getId();
                            intent.putExtra("ID", id);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}

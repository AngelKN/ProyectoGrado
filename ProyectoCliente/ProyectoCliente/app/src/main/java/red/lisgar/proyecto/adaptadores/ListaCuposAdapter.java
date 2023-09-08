package red.lisgar.proyecto.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import red.lisgar.proyecto.R;
import red.lisgar.proyecto.admin.AdminActualizarCupoActivity;
import red.lisgar.proyecto.entidades.Cupo;

public class ListaCuposAdapter extends RecyclerView.Adapter<ListaCuposAdapter.CuposViewHolder> {

    ArrayList<Cupo> listaOriginal;
    ArrayList<Cupo> listItem;
    Context context;
    String destino;
    String ventana;

    public ListaCuposAdapter(ArrayList<Cupo> listItem, Context context, String destino, String ventana) {
        this.listItem = listItem;
        this.context = context;
        this.destino = destino;
        this.ventana = ventana;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listItem);
    }

    @NonNull
    @Override
    public CuposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //CUAL LAYOUT SE UTILIZARA PARA EL ADAPTER
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new CuposViewHolder(view);
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
                return new CuposViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + ventana);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CuposViewHolder holder, int position) {
        final Cupo item = listItem.get(position);

        //MANIPULACION DE DATOS EN EL LAYOUT
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                holder.precio.setText("Valor:  "+item.getPrecio()+"");
                holder.horaI.setText("Desde: "+item.getHora_llegada());
                holder.horaS.setText("Hasta: "+item.getHora_salida());
                holder.descripcion.setText("Descripcion: "+item.getDescripcion());
                break;
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                holder.precioU.setText("$"+item.getPrecio());
                holder.horaIU.setText(item.getHora_llegada());
                holder.horaSU.setText(item.getHora_salida());
                holder.descripcionU.setText(item.getDescripcion());
                break;
        }
    }

    //FILTRAR LA LISTA DE PUBLICACIONES DE ACUERDO A LA DESCRIPCION
    public void filter(String buscar){
        int longitud = buscar.length();

        if(longitud == 0){
            listItem.clear();
            listItem.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Cupo> collection = listItem.stream().filter(i -> i.getDescripcion().toLowerCase().contains(buscar.toLowerCase())).collect(Collectors.toList());
                listItem.clear();
                listItem.addAll(collection);
            }else{
                for (Cupo c : listaOriginal){
                    if(c.getDescripcion().toLowerCase().contains(buscar.toLowerCase())){
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

    public class CuposViewHolder extends RecyclerView.ViewHolder {

        TextView precio;
        TextView horaI;
        TextView horaS;
        TextView descripcion;

        TextView precioU;
        TextView horaIU;
        TextView horaSU;
        TextView descripcionU;


        public CuposViewHolder(@NonNull View itemView) {
            super(itemView);
            precio = itemView.findViewById(R.id.txtItem1);
            horaI = itemView.findViewById(R.id.txtItem2);
            horaS = itemView.findViewById(R.id.txtItem3);
            descripcion = itemView.findViewById(R.id.txtItem4);

            precioU = itemView.findViewById(R.id.txtItemPrecioU);
            horaIU = itemView.findViewById(R.id.txtItemHoraIU);
            horaSU = itemView.findViewById(R.id.txtItemHoraSU);
            descripcionU = itemView.findViewById(R.id.txtItemDescripcionU);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    String id;

                    //DIRECCIONAMIENTO
                    switch (destino){
                        case "ACTUALIZAR":
                            intent = new Intent(context, AdminActualizarCupoActivity.class);
                            id = listItem.get(getAdapterPosition()).getId();
                            intent.putExtra("idi", id);
                            context.startActivity(intent);
                            break;
                        case "NINGUNO":
                            break;
                    }
                }
            });
        }
    }
}

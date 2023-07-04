package ufscar.dc.Pokedex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

internal class GridAdapter(
    private  val cList: List<GridModal>,
    private val ctx: Context
):
    BaseAdapter(){

        private  var layoutInflater: LayoutInflater? = null
        private  lateinit var cIV: ImageView

        override fun getCount(): Int{
            return cList.size
        }

        override fun getItem(p0: Int): Any? {
            return null
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var convView = p1

            if(layoutInflater == null){
                layoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }

            if(convView == null){
                convView = layoutInflater!!.inflate(R.layout.grid_item, null)
            }

            cIV = convView!!.findViewById(R.id.idIVCourse)
            cIV.setImageResource(cList.get(p0).courseImg)

            return convView
        }
    }


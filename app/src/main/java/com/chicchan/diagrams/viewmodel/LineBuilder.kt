package com.chicchan.diagrams.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import com.chicchan.diagrams.common.position
import com.chicchan.diagrams.model.Shape





//define the cuadrante of the destiny if the origin is the origin_items's position
enum class cuadrante
{
    upRigth,
    upLeft,
    downRigth,
    downLeft,
    up,
    down,
    left,
    rigth,

}
//the kind of oposition between to items'postions
enum class itemOposition
{
    direc,
    oposite,
    lateral,
    lateralDirect

}
enum class Align
{
    horizontal,
    vertical
}


class LineBuilder {





private fun print(text:String?="")
{
    if(text!!.isNotEmpty()) {
        Log.d(TAG, "print: $text")

    }

}

//tells if item is direct to 2 cuadrantes
private fun itemIsDirect(pos: position, cuad:cuadrante):Pair<Boolean,Align>
{
    when(pos){

        position.UP->{
            when(cuad)
                    {
                        cuadrante.upRigth, cuadrante.upLeft, cuadrante.up-> return Pair(true,Align.horizontal)
                        cuadrante.downRigth, cuadrante.downLeft, cuadrante.down->return Pair(false,Align.horizontal)
                        cuadrante.left->print()
                        cuadrante.rigth->print()
                    }
        }

        position.DOWN-> {
            when(cuad)
                    {
                cuadrante.upRigth, cuadrante.upLeft, cuadrante.up->
                        return Pair(false, Align.horizontal)
                cuadrante.downRigth, cuadrante.downLeft, cuadrante.down->
                        return Pair(true, Align.horizontal)
                cuadrante.left->
                        print()
                cuadrante.rigth->
                        print()
                    }
        }
        position.RIGTH-> {
            when(cuad)
                    {
                cuadrante.upRigth, cuadrante.downRigth, cuadrante.rigth->
                        return Pair(true, Align.vertical)
                cuadrante.upLeft, cuadrante.downLeft, cuadrante.left->
                        return Pair(false, Align.vertical)
                cuadrante.up->
                        print()
                cuadrante.down->
                        print()
                    }
        }
        position.LEFT-> {
            when(cuad)
                    {
                cuadrante.upRigth, cuadrante.downRigth, cuadrante.rigth->
                        return Pair(false, Align.vertical)
                cuadrante.upLeft, cuadrante.downLeft, cuadrante.left->
                        return Pair(true, Align.vertical)
                cuadrante.up->
                        print()
                cuadrante.down->
                        print()
                    }
        }
    }
    return Pair(false,Align.vertical)

}




private fun getCuadrante(item1:Pair<Shape,position>, item2:Pair<Shape,position>):cuadrante
{



    val iniPoint:Pair<Float,Float> =when(item1.second) {
        position.UP->item1.first.positionUp

        position.DOWN->item1.first.positionDown
        position.LEFT->item1.first.positionLeft
        position.RIGTH->item1.first.positionRight
    }

    val endPoint:Pair<Float,Float> =when(item2.second) {
        position.UP->item2.first.positionUp
        position.DOWN->item2.first.positionDown
        position.LEFT->item2.first.positionLeft
        position.RIGTH->item2.first.positionRight
    }



    if (iniPoint.first ==endPoint.first)
    {
        if(iniPoint.second<endPoint.second)
        {  return cuadrante.down}
        else
        {return cuadrante.up}
    }
    else if(iniPoint.second==endPoint.second)
    {
        if(iniPoint.first<endPoint.first)
        {  return cuadrante.rigth}
        else
        {return cuadrante.left}
    }
    else if(iniPoint.first<endPoint.first)
    {//rigth
        if(iniPoint.second<endPoint.second)
            {return cuadrante.downRigth}
         else
            {return cuadrante.upRigth}
    }
    else if(iniPoint.first>endPoint.first)
    {//left
        if(iniPoint.second<endPoint.second)
        {return cuadrante.downLeft}
        else
        {return cuadrante.upLeft}
    }

    return cuadrante.up
}



private fun itemsOposition(pos1:position , pos2:position,itemCuadranteIsDirect:Boolean,  cua:cuadrante): itemOposition
{
    when(pos1) {

        position.UP->
        when(pos2) {
            position.UP->
            if(itemCuadranteIsDirect){return itemOposition.oposite}
            else {return itemOposition.direc}
            position.DOWN->
            if(itemCuadranteIsDirect){return itemOposition.direc}
            else {return itemOposition.oposite}
            position.RIGTH->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateral
                cuadrante.upLeft->
                        return itemOposition.lateralDirect
                cuadrante.downRigth->
                        return itemOposition.lateral
                cuadrante.downLeft->
                        return itemOposition.lateralDirect
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }

            position.LEFT->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateralDirect
                cuadrante.upLeft->
                        return itemOposition.lateral
                cuadrante.downRigth->
                        return itemOposition.lateralDirect
                cuadrante.downLeft->
                        return itemOposition.lateral
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }
        }
        position.DOWN->
        when(pos2) {

            position.UP->
            if(itemCuadranteIsDirect){return itemOposition.direc}
            else {return itemOposition.oposite}
            position.DOWN->
            if(itemCuadranteIsDirect){return itemOposition.oposite}
            else {return itemOposition.direc}
            position.RIGTH->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateral
                cuadrante.upLeft->
                        return itemOposition.lateralDirect
                cuadrante.downRigth->
                        return itemOposition.lateral
                cuadrante.downLeft->
                        return itemOposition.lateralDirect
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }
            position.LEFT->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateralDirect
                cuadrante.upLeft->
                        return itemOposition.lateral
                cuadrante.downRigth->
                        return itemOposition.lateralDirect
                cuadrante.downLeft->
                        return itemOposition.lateral
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }
        }
        position.RIGTH->
           when(pos2) {
            position.UP->
              when(cua)
                    {

                  cuadrante.upRigth->
                        return itemOposition.lateral
                  cuadrante.upLeft->
                        return itemOposition.lateral
                  cuadrante.downRigth->
                        return itemOposition.lateralDirect
                  cuadrante.downLeft->
                        return itemOposition.lateralDirect
                  cuadrante.up->
                        return itemOposition.lateral
                  cuadrante.down->
                        return itemOposition.lateral
                  cuadrante.left->
                        return itemOposition.lateral
                  cuadrante.rigth->
                        return itemOposition.lateral
                    }
            position.DOWN->
               when(cua)
                    {

                   cuadrante.upRigth->
                        return itemOposition.lateralDirect
                   cuadrante.upLeft->
                        return itemOposition.lateralDirect
                   cuadrante.downRigth->
                        return itemOposition.lateral
                   cuadrante.downLeft->
                        return itemOposition.lateral
                   cuadrante.up->
                        return itemOposition.lateral
                   cuadrante.down->
                        return itemOposition.lateral
                   cuadrante.left->
                        return itemOposition.lateral
                   cuadrante.rigth->
                        return itemOposition.lateral
                    }
            position.RIGTH->
            if(itemCuadranteIsDirect){return itemOposition.oposite}
            else {return itemOposition.direc}
            position.LEFT->
            if(itemCuadranteIsDirect){return itemOposition.direc}
            else {return itemOposition.oposite}
        }
        position.LEFT->
        when(pos2){

            position.UP->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateral
                cuadrante.upLeft->
                        return itemOposition.lateral
                cuadrante.downRigth->
                        return itemOposition.lateralDirect
                cuadrante.downLeft->
                        return itemOposition.lateralDirect
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }
            position.DOWN->
            when(cua)
                    {

                cuadrante.upRigth->
                        return itemOposition.lateralDirect
                cuadrante.upLeft->
                        return itemOposition.lateralDirect
                cuadrante.downRigth->
                        return itemOposition.lateral
                cuadrante.downLeft->
                        return itemOposition.lateral
                cuadrante.up->
                        return itemOposition.lateral
                cuadrante.down->
                        return itemOposition.lateral
                cuadrante.left->
                        return itemOposition.lateral
                cuadrante.rigth->
                        return itemOposition.lateral
                    }
            position.RIGTH->
            if(itemCuadranteIsDirect){return itemOposition.direc}
            else {return itemOposition.oposite}
            position.LEFT->
            if(itemCuadranteIsDirect){return itemOposition.oposite}
            else {return itemOposition.direc}
        }
    }


}








    fun createLine(item1:Pair<Shape,position>,item2:Pair<Shape,position>):List<Pair<Double,Double>>
    {

        val iniPoint:Pair<Double,Double> =when(item1.second) {
            position.UP->item1.first.positionUp as Pair<Double, Double>

            position.DOWN->item1.first.positionDown as Pair<Double, Double>
            position.LEFT->item1.first.positionLeft as Pair<Double, Double>
            position.RIGTH->item1.first.positionRight as Pair<Double, Double>
        }

        val endPoint:Pair<Double,Double> =when(item2.second) {
            position.UP->item2.first.positionUp as Pair<Double, Double>
            position.DOWN->item2.first.positionDown as Pair<Double, Double>
            position.LEFT->item2.first.positionLeft as Pair<Double, Double>
            position.RIGTH->item2.first.positionRight as Pair<Double, Double>
        }
        val itemIniSize=item1.first.size
        val itemEndSize=item2.first.size
        val itemEndCenter=item2.first.center
        val itemIniCenter=item1.first.center
        val destinyPosition=getCuadrante(Pair(item1.first,item1.second), Pair(item2.first,item2.second))
        val direct=itemIsDirect(item1.second, destinyPosition)
        var translapey=(iniPoint.second < (itemEndCenter.second + (itemEndSize.second/2)))&&(iniPoint.second > (itemEndCenter.second - (itemEndSize.second/2)))
        print(translapey)
        val translapex=(iniPoint.first < (itemEndCenter.first + (itemEndSize.first/2)))&&(iniPoint.first > (itemEndCenter.first - (itemEndSize.first/2)))



        if(direct.first){
            //destiny item is direct from the origin
            val itemOp = itemsOposition(item1.second, item2.second,  true,destinyPosition)
            when(itemOp){

                itemOposition.direc->
                
                 when(direct.second){

                    Align.vertical->
                    return listOf(iniPoint,Pair(iniPoint.first+((endPoint.first-iniPoint.first)/2), iniPoint.second),Pair(iniPoint.first+((endPoint.first-iniPoint.first)/2), endPoint.second),endPoint)
                     Align.horizontal->
                    return listOf(iniPoint,Pair(iniPoint.first, iniPoint.second + ((endPoint.second-iniPoint.second)/2) ),Pair(endPoint.first, iniPoint.second + ((endPoint.second-iniPoint.second)/2) ),endPoint)
                }

                itemOposition.oposite->{
                var convy=1.0
                var convx=1.0
                if(iniPoint.second>endPoint.second) {convy = (-1.0)}
                if(iniPoint.first>endPoint.first) {convx = (-1.0)}

                when(direct.second){

                    Align.vertical-> {
                        if(translapey)
                        {
                            return listOf(
                                iniPoint,
                                Pair(iniPoint.first + (10 * convx), iniPoint.second),
                                Pair(iniPoint.first + (10 * convx),(endPoint.second + ((itemEndSize.second /2) + 10) * convy)),
                                Pair(endPoint.first+(30*convx),  (endPoint.second+((itemEndSize.second/2)+10)*convy)),
                                Pair(endPoint.first+(30*convx), endPoint.second), endPoint)
                        }
                        return listOf(
                            iniPoint,
                            Pair(endPoint.first + (30 * convx), iniPoint.second),
                            Pair(endPoint.first + (30 * convx), endPoint.second),
                            endPoint
                        )
                    }
                    Align.horizontal-> {
                        if(translapex) {

                            return listOf(
                                iniPoint,
                                Pair(iniPoint.first, iniPoint.second + (10 * convy)),
                                Pair((endPoint.first + ((itemEndSize.first / 2) + 10) * convx),
                                iniPoint.second + (10 * convy)),
                                Pair((endPoint.first+((itemEndSize.first/2)+10)*convx), endPoint.second+(30*convy)), 
                                Pair(endPoint.first, endPoint.second+(30*convy)), endPoint)
                        }
                        return listOf(
                            iniPoint,
                            Pair(iniPoint.first, endPoint.second + (30 * convy)),
                            Pair(endPoint.first, endPoint.second + (30 * convy)),
                            endPoint
                        )
                    }
                }

            }
            itemOposition.lateral->
                when(direct.second){

                    Align.vertical->{
                           var convx = 1.0
                           var convy = 1.0
                            if (iniPoint.second>endPoint.second) {convy = (-1.0)}
                            if (iniPoint.first>endPoint.first) {convx = (-1.0)}

                            if(translapey)
                            {
                                return listOf(iniPoint,Pair(iniPoint.first+(10*convx), iniPoint.second) ,Pair(iniPoint.first+(10*convx), (endPoint.second + ((itemEndSize.second/2)+10)*convy)), Pair(endPoint.first,  (endPoint.second + ((itemEndSize.second/2)+10)*convy)) ,  endPoint)
                            }

                            return listOf(iniPoint,Pair(endPoint.first+((itemEndSize.first/2)+30)*convx , iniPoint.second),Pair(endPoint.first+((itemEndSize.first/2)+30)*convx, endPoint.second + (30*convy)) , Pair(endPoint.first, endPoint.second + (30*convy)), endPoint)
                    }
                    Align.horizontal->{
                            var convy=1.0
                            var convx=1.0
                            if(iniPoint.second>endPoint.second) {convy = (-1.0)}
                            if(iniPoint.first>endPoint.first) {convx = (-1.0)}

                            if(translapex)
                            {

                                return listOf(iniPoint,Pair(iniPoint.first, iniPoint.second+(10*convy)),
                                    Pair((endPoint.first + ((itemEndSize.first/2)+10)*convx), iniPoint.second+(10*convy) ),
                                    Pair((endPoint.first + ((itemEndSize.first/2)+10)*convx) , endPoint.second ) ,  endPoint)
                            }

                            return listOf(iniPoint,Pair(iniPoint.first ,
                                endPoint.second+((itemEndSize.second/2)+30)*convy),
                                Pair(endPoint.first + (30*convx), endPoint.second+((itemEndSize.second/2)+30)*convy) ,
                                Pair(endPoint.first + (30*convx), endPoint.second ),
                                endPoint)

                            }
                 }



            itemOposition.lateralDirect->{
                print("lateral d")
                var convy=1.0
                var convx=1.0
                if(iniPoint.second>endPoint.second){convy = (-1.0)}
                if(iniPoint.first>endPoint.first){convx = (-1.0)}
                 when(direct.second){

                    Align.vertical-> {
                        if (translapey) {
                            return listOf(
                                iniPoint,
                                Pair(iniPoint.first + (10 * convx), iniPoint.second),
                                Pair(
                                    iniPoint.first + (10 * convx),
                                    (endPoint.second + ((itemEndSize.second / 2) + 10) * convy)
                                ),
                                Pair(
                                    endPoint.first,
                                    (endPoint.second + ((itemEndSize.second / 2) + 10) * convy)
                                ),
                                endPoint
                            )
                        }

                        return listOf(iniPoint, Pair(endPoint.first, iniPoint.second), endPoint)
                    }
                    Align.horizontal->
                        return listOf(iniPoint, Pair(iniPoint.first, endPoint.second), endPoint)
                    }
             }
            }
        }
        else
        {

            //no cuadrante direct

            var convy=1.0F
            var convx=1.0F
            if (iniPoint.second<endPoint.second) {convy = (-1F)}
            if (iniPoint.first<endPoint.first) {convx = (-1F)}

            var itemOp = itemsOposition(item1.second, item2.second,  false, destinyPosition)

            when(itemOp)
                    {

                        itemOposition.direc->
                        when(direct.second){

                            Align.vertical-> {
                                if (translapey) {
                                    return listOf(
                                        iniPoint,
                                        Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                        Pair(
                                            iniPoint.first + (30 * convx),
                                            iniPoint.second + (10 * convy) + ((itemIniSize.second / 2) * convy)
                                        ),
                                        Pair(
                                            iniPoint.first - ((itemIniSize.first + 10) * convx),
                                            iniPoint.second + (10 * convy) + ((itemIniSize.second / 2) * convy)
                                        ),
                                        Pair(
                                            iniPoint.first - ((itemIniSize.first + 10) * convx),
                                            endPoint.second
                                        ),
                                        endPoint
                                    )
                                }
                                return listOf(
                                    iniPoint,
                                    Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                    Pair(iniPoint.first + (30 * convx), endPoint.second),
                                    endPoint
                                )
                            }
                            Align.horizontal-> {
                                if (translapex) {
                                    return listOf(
                                        iniPoint,
                                        Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                        Pair(
                                            iniPoint.first + (10 * convx) + ((itemIniSize.first / 2) * convx),
                                            iniPoint.second + (30 * convy)
                                        ),
                                        Pair(
                                            iniPoint.first + (10 * convx) + ((itemIniSize.first / 2) * convx),
                                            iniPoint.second - ((itemIniSize.second + 10) * convy)
                                        ),
                                        Pair(
                                            endPoint.first,
                                            iniPoint.second - ((itemIniSize.second + 10) * convy)
                                        ),
                                        endPoint
                                    )
                                }

                                return listOf(
                                    iniPoint,
                                    Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                    Pair(endPoint.first, iniPoint.second + (30 * convy)),
                                    endPoint
                                )
                            }
                        }
                        itemOposition.oposite->
                        when(direct.second) {

                            Align.vertical -> {
                                if (translapex) {
                                    return listOf(
                                        iniPoint,
                                        Pair(
                                            endPoint.first + ((30 + itemEndSize.first) * convx),
                                            iniPoint.second
                                        ),
                                        Pair(
                                            endPoint.first + ((30 + itemEndSize.first) * convx),
                                            endPoint.second - ((30 + itemEndSize.second / 2) * convy)
                                        ),
                                        Pair(
                                            endPoint.first - (30 * convx),
                                            endPoint.second - ((30 + itemEndSize.second / 2) * convy)
                                        ),
                                        Pair(endPoint.first - (30 * convx), endPoint.second),
                                        endPoint
                                    )
                                }

                                return listOf(
                                    iniPoint,
                                    Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                    Pair(
                                        iniPoint.first + (30 * convx),
                                        endPoint.second - ((30 + itemEndSize.second / 2) * convy)
                                    ),
                                    Pair(
                                        endPoint.first - (30 * convx),
                                        endPoint.second - ((30 + itemEndSize.second / 2) * convy)
                                    ),
                                    Pair(endPoint.first - (30 * convx), endPoint.second), endPoint
                                )
                            }
                            Align.horizontal ->
                            {
                                if (translapey) {
                                    return listOf(
                                        iniPoint,
                                        Pair(
                                            iniPoint.first,
                                            endPoint.second + ((itemEndSize.second + 10) * convy)
                                        ),
                                        Pair(
                                            endPoint.first - ((30 + itemEndSize.first / 2) * convx),
                                            endPoint.second + ((itemEndSize.second + 10) * convy)
                                        ),
                                        Pair(
                                            endPoint.first - ((30 + itemEndSize.first / 2) * convx),
                                            endPoint.second - (30 * convy)
                                        ),
                                        Pair(endPoint.first, endPoint.second - (30 * convy)),
                                        endPoint
                                    )
                                }


                            return listOf(
                                iniPoint, Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                Pair(
                                    endPoint.first - ((30 + itemEndSize.first / 2) * convx),
                                    iniPoint.second + (30 * convy)
                                ),
                                Pair(
                                    endPoint.first - ((30 + itemEndSize.first / 2) * convx),
                                    endPoint.second - (30 * convy)
                                ), Pair(endPoint.first, endPoint.second - (30 * convy)), endPoint
                            )
                        }
                        }
                itemOposition.lateral->
                           when(direct.second) {

                               Align.vertical -> {

                                   if (translapey) {
                                       return listOf(
                                           iniPoint,
                                           Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                           Pair(
                                               iniPoint.first + (30 * convx),
                                               endPoint.second - ((10 + (itemEndSize.second / 2)) * convy)
                                           ),
                                           Pair(
                                               endPoint.first,
                                               endPoint.second - ((10 + (itemEndSize.second / 2)) * convy)
                                           ),
                                           endPoint
                                       )
                                   }


                                   return listOf(
                                       iniPoint,
                                       Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                       Pair(
                                           iniPoint.first + (30 * convx),
                                           endPoint.second - (30 * convy)
                                       ),
                                       Pair(endPoint.first, endPoint.second - (30 * convy)),
                                       endPoint
                                   )
                               }
                               Align.horizontal ->{

                                   if (translapex) {


                                       return listOf(
                                           iniPoint,
                                           Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                           Pair(
                                               endPoint.first - ((10 + (itemEndSize.first / 2)) * convx),
                                               iniPoint.second + (30 * convy)
                                           ),
                                           Pair(
                                               endPoint.first - ((10 + (itemEndSize.first / 2)) * convx),
                                               endPoint.second
                                           ),
                                           endPoint
                                       )

                                   }
                               if (translapey) {
                                   return listOf(
                                       iniPoint,
                                       Pair(
                                           iniPoint.first,
                                           iniPoint.second + (((itemEndSize.second / 2) + 30) * convy)
                                       ),
                                       Pair(
                                           endPoint.first - (30 * convx),
                                           iniPoint.second + (((itemEndSize.second / 2) + 30) * convy)
                                       ),
                                       Pair(endPoint.first - (30 * convx), endPoint.second),
                                       endPoint
                                   )

                               }
                                   return listOf(
                                   iniPoint,
                                   Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                   Pair(
                                       endPoint.first - (30 * convx),
                                       iniPoint.second + (30 * convy)
                                   ),
                                   Pair(endPoint.first - (30 * convx), endPoint.second),
                                   endPoint
                               )
                           }
                        }
                        itemOposition.lateralDirect->{
                        val translapeyO=(endPoint.second < (itemIniCenter.second + (itemIniSize.second/2)))&&(endPoint.second > (itemIniCenter.second - (itemIniSize.second/2)))
                        val translapexO=(endPoint.first < (itemIniCenter.first + (itemIniSize.first/2)))&&(endPoint.first > (itemIniCenter.first - (itemIniSize.first/2)))

                        when(direct.second) {
                            Align.vertical -> {

                                if (translapexO) {
                                    return listOf(
                                        iniPoint,
                                        Pair(iniPoint.first + (10 * convx), iniPoint.second),
                                        Pair(
                                            iniPoint.first + (10 * convx),
                                            iniPoint.second - ((10 + itemEndSize.first / 2) * convy)
                                        ),
                                        Pair(
                                            endPoint.first,
                                            iniPoint.second - ((10 + itemEndSize.first / 2) * convy)
                                        ), endPoint
                                    )
                                }
                                return listOf(
                                    iniPoint,
                                    Pair(iniPoint.first + (30 * convx), iniPoint.second),
                                    Pair(
                                        iniPoint.first + (30 * convx),
                                        iniPoint.second + ((30 + itemEndSize.first / 2) * convy)
                                    ),
                                    Pair(
                                        endPoint.first,
                                        iniPoint.second + ((30 + itemEndSize.first / 2) * convy)
                                    ),
                                    endPoint
                                )
                            }
                            Align.horizontal ->{
                                if(translapeyO)
                                {
                                    return listOf(
                                        iniPoint,
                                        Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                        Pair(
                                            iniPoint.first - ((30 + itemEndSize.second / 2) * convx),
                                            iniPoint.second + (30 * convy)
                                        ),
                                        Pair(
                                            iniPoint.first - ((30 + itemEndSize.second / 2) * convx),
                                            endPoint.second
                                        ), endPoint
                                    )
                                }
                                return listOf(
                                iniPoint,
                                Pair(iniPoint.first, iniPoint.second + (30 * convy)),
                                Pair(
                                    iniPoint.first + ((30 + itemEndSize.first / 2) * convx),
                                    iniPoint.second + (30 * convy)
                                ),
                                Pair(
                                    iniPoint.first + ((30 + itemEndSize.first / 2) * convx),
                                    endPoint.second
                                ),
                                endPoint
                            )
                        }
                        }
                        }
                    }

        }

        val point=item1.first.positionLeft as Pair<Double,Double>
        return listOf(point,Pair( point.first+100,  point.second),
            Pair( point.first+100,  point.second+50),
            Pair( point.first+150,  point.second+50))
    }



}//end classs
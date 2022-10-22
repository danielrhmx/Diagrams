package com.chicchan.diagrams.model

import com.chicchan.diagrams.common.position


data class Connection(val shapeIni: Shape, val pointInit: position, val shapeEnd: Shape, val pointEnd: position) {

}
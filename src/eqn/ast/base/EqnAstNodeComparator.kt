package eqn.ast.base

class EqnAstNodeComparator : Comparator<EqnAstNode> {
    override fun compare(o1: EqnAstNode?, o2: EqnAstNode?): Int {
        if (o1 == null || o2 == null) {
            return 0
        }
        if (o1.type == EqnAstNode.Type.Constant) {
            return if (o2.type == EqnAstNode.Type.Constant) 0 else 1
        }
        if (o1.type == EqnAstNode.Type.Variable) {
            return if (o2.type == EqnAstNode.Type.Variable) o1.value.compareTo(o2.value) else 1
        }
        return if (o1.type == o2.type) o1.value.compareTo(o2.value) else o1.type.compareTo(o2.type)
    }
}
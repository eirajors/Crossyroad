
  public Player movePlayer(Direction dir) {
    if (dir == Direction.LEFT) {
      pos = new CellPosition(pos.row(), pos.col()-1 );  

      return new Player(pos, chr);
    }
    if (dir == Direction.RIGHT) {
      pos = new CellPosition(pos.row(), pos.col()+1 );  
      
      return new Player(pos, chr);
    }
    if (dir == Direction.UP) {
      pos = new CellPosition(pos.row() - 1, pos.col());  
      
      return new Player(pos, chr);
    }
    if (dir == Direction.DOWN) {
      pos = new CellPosition(pos.row() + 1, pos.col());  
      
      return new Player(pos, chr);
    }
    return new Player(pos, chr);
  }